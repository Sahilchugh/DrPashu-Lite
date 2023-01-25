package com.drpashu.sdk.network;

import androidx.annotation.Nullable;

public interface NetworkingInterface {

    <T> void networkingRequest(@Nullable MethodType methodType, boolean status, @Nullable T error, Object o);

    enum MethodType{
        login, signup, updateVaccination, fetchTransactions, results, fetchFinanceData, fetchLot,
        fetchLotFinance, dashboardInfo, weatherInfo, search, addFarmer, cooperative, uploadImage,
        overdueList, completedList, upcomingList, lotVaccine, symptomsList, getHealthImages,
        checkVersion, updateLanguage, updateVetDetails, getRazorpayOrderId, updateOnboardingStatus,
        vetDashboardData, updateVetStatus, getAnimals, getVetList, getProfile, updateProfile,
        startCall, uploadPrescription, getCallHistoryList, getCallDetail, updateCallStatus,
        updateProfilePicture, getMortalityData, getDiseaseList, addMortalityData, rejectCall,
        updateDeviceToken, getAnimalList, fetchFeedData, updateFeedUsed, fetchProduceData,
        updateProduceAdd, callBackUser, postErrorDetails, fetchBalance, fetchWalletTransaction,
        addCoinsToWallet, updateVetSchedule, getUserList, getUserDetail, getTransactionTypeList,
        makeTransaction, callUserById, createPost, fetchAddaPosts, fetchPostReplies, createReply, postAction, searchAddaPosts, getFarmList
    }
}
