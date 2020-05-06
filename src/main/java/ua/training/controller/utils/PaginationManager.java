package ua.training.controller.utils;

import ua.training.constants.AppConstants;

public class PaginationManager {
	
	private static final int OFFSET_CALC_VALUE = 1;
	private static final double ODD_RECORDS_NUMBER_PAGES_NUMBER_VALUE = 1.0;	

	private PaginationManager() {

	}

	private static class Holder {
		static final PaginationManager INSTANCE = new PaginationManager();
	}

	public static PaginationManager getInstance() {
		return Holder.INSTANCE;
	}

	public int getOffset(int page) {
		return (page - OFFSET_CALC_VALUE) * AppConstants.LIMIT;

	}

	public int getNumberOfPages(int numberOfRecords) {
		return (int) Math.ceil(numberOfRecords * ODD_RECORDS_NUMBER_PAGES_NUMBER_VALUE / AppConstants.LIMIT);
	}
}
