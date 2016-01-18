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

public class DoctorSelectionActivity extends Activity {
	// The file with all of the Patient's info.
	public static final String FILENAME = "patientdata.txt";
	private Patient patient;
	private PatientDataBase database;
	private String cardNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_selection);
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		cardNumber = (String) intent.getSerializableExtra("cardNumberKey");
		database = (PatientDataBase) intent.getSerializableExtra("databaseKey");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_selection, menu);
		return true;
	}

	public void doctorView(View view) {
		Intent viewIntent = new Intent(this, DoctorViewActivity.class);

		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById(R.id.patient_card_number_field_in_doctor_selection);
		String cardNumber = cardNumberText.getText().toString();
		Patient patient = database.getPatientByCardNumber(cardNumber);

		if (patient != null) {
			viewIntent.putExtra("patientKey", patient);
			startActivity(viewIntent);
		} else {
			String pushInfo = "Cannot find this patient!";
		}
	}

	public void doctorUpdate(View view) {
		Intent updateIntent = new Intent(this, DoctorUpdateActivity.class);
		updateIntent.putExtra("databaseKey", database);
		startActivity(updateIntent);
	}

	public void doctorSave(View view) {
		if (database != null)
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0);
				database.saveToFile(outputStream);
				String pushInfo = "Patient info updated!";
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
}
