package com.example.csc207app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DoctorUpdateActivity extends Activity {
	public static final String FILENAME = "patientdata.txt";
	private PatientDataBase database;
	// The Patient with info and is sent to the text file
	private Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_update);
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		database = (PatientDataBase) intent.getSerializableExtra("databaseKey");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_update, menu);
		return true;
	}

	public void updatePrescription(View view) {
		Intent intent = new Intent(this, DoctorSelectionActivity.class);
		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById(R.id.patient_cardNumber_field_for_prescription);
		String cardNumber = cardNumberText.getText().toString();

		// Gets the account number from the first EditText field.
		EditText prescriptionText = (EditText) findViewById(R.id.prescription);
		String prescription = prescriptionText.getText().toString();

		Patient patient = database.getPatientByCardNumber(cardNumber);

		// Saves all Persons managed by manager to file.
		if (patient != null) { // If the patient is empty
			patient.setPrescription(prescription);
			// Adds the three main components that is needed to create Patient

		} else {
			String pushInfo = "Cannot find this patient!";
		}
	}

	public void save(View view) {
		if (database != null)
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0);
				database.saveToFile(outputStream);
				String pushInfo = "Patient info updated!";
				Intent intent = new Intent(this, DoctorSelectionActivity.class);
				intent.putExtra("databaseKey", database);
				startActivity(intent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
}
