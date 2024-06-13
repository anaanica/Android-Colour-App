package hr.algebra.colourapp.factory

import android.content.Context
import hr.algebra.colourapp.dao.ColourSqlHelper

fun getColourRepository(context: Context?) = ColourSqlHelper(context)