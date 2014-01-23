package com.rebeccamills.loader;

import java.io.FileReader;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class InfluenzaLoader {

	public static void main(String[] args) throws Exception {
		
        //Checks for legitimate file name
		if (args.length < 1) {
			System.out.println("No file name: InfluenzaLoader <filename>");
			System.exit(0);
		}
        
		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(new FileReader(args[0]),
					CsvPreference.TAB_PREFERENCE);

			beanReader.getHeader(true);
			
            //"null" for skipped columns in text file
			final String[] header = new String[] { "strain_id", "subtype",
					"collection_date", "state", null, null, null, "age", null,
					null, null, null };

			Session session = SimpleClient.getInstance();
			
			
			PreparedStatement statement_year_subtype = session.prepare(

			"INSERT INTO influenza.year_subtype"
					+ "(strain_id, subtype, collection_date, state, age, year)"
					+ "VALUES (?,?,?,?,?,?);");

			PreparedStatement statement_state_date = session.prepare(

			"INSERT INTO influenza.state_date"
					+ "(strain_id, subtype, collection_date, state, age, year)"
					+ "VALUES (?,?,?,?,?,?);");

			PreparedStatement statement_state_subtype = session.prepare(

			"INSERT INTO influenza.state_subtype"
					+ "(strain_id, subtype, collection_date, state, age, year)"
					+ "VALUES (?,?,?,?,?,?);");

			BoundStatement boundStatement_year_subtype = new BoundStatement(
					statement_year_subtype);
			BoundStatement boundStatement_state_date = new BoundStatement(
					statement_state_date);
			BoundStatement boundStatement_state_subtype = new BoundStatement(
					statement_state_subtype);

			UserBean flu;
			int i = 0;
			while ((flu = beanReader.read(UserBean.class, header)) != null) {

				SimpleClient.getInstance().execute(
						boundStatement_year_subtype.bind(

						flu.getStrain_id(), flu.getSubtype(), flu
								.getCollection_date().getTime(),
								flu.getState(), flu.getAge(), flu.getYear()));

				SimpleClient.getInstance().execute(
						boundStatement_state_date.bind(

						flu.getStrain_id(), flu.getSubtype(), flu
								.getCollection_date().getTime(),
								flu.getState(), flu.getAge(), flu.getYear()));

				SimpleClient.getInstance().execute(
						boundStatement_state_subtype.bind(

						flu.getStrain_id(), flu.getSubtype(), flu
								.getCollection_date().getTime(),
								flu.getState(), flu.getAge(), flu.getYear()));

				i++;
			}
			System.out.println(i);

		} finally {
			beanReader.close();
			
		}
		 SimpleClient.close();
	}

}
