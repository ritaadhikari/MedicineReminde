package com.example.medicinetime;

import android.content.Context;
import androidx.annotation.NonNull;

import com.example.medicinetime.data.source.MedicineRepository;
import com.example.medicinetime.data.source.local.MedicinesLocalDataSource;


public class Injection {

    public static MedicineRepository provideMedicineRepository(@NonNull Context context) {
        return MedicineRepository.getInstance(MedicinesLocalDataSource.getInstance(context));
    }
}
