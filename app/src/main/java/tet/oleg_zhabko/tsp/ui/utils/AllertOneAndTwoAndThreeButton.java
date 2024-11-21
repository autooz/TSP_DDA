package tet.oleg_zhabko.tsp.ui.utils;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;
import tet.oleg_zhabko.tsp.R;
public class AllertOneAndTwoAndThreeButton {
    public AlertDialog.Builder builder = null;
    public AlertDialog bs;
    private Activity mActivity;
    private String pseudo_tag = "allertOneAndTwoAndThreeButton";

    public AlertDialog createTwoButtonsAlertDialog(Activity activity, String title, String content, final Intent intent, int function,  AllertDialogCallbackInterface<String> callback) {

        TetDebugUtil.d(pseudo_tag, "title=[" + title + "] content=[" + content + "]");

        if (builder == null) {
            mActivity = activity;
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton(R.string.doNotSave, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                //   showMessage("Нажали Нет");
                if (function == 1){
                    callback.allertDialogCallback(false);
                }
                bs.dismiss();
            }
        });
        builder.setPositiveButton(R.string.save,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //   showMessage("Нажали ОК");
                        if (function == 1) {
                            callback.allertDialogCallback(true);
                           bs.dismiss();
                        }

                        if (intent != null) {
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mActivity.startActivity(intent);
                            mActivity.finish();
                        }
                    }
                });
        try {
            if (bs == null) {
                bs = builder.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs;
    }


    public AlertDialog createOneButtonsAlertDialog(Activity activity, String title, String content, final Intent intent_or_null) {

        bs = null;
        pseudo_tag = "createOneButtonsAlert";
        TetDebugUtil.d(pseudo_tag, "title=[" + title + "] content=[" + content + "]");
        mActivity = activity;
        if (builder == null) {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(title);
        builder.setMessage(content);
//        builder.setNegativeButton("NO",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,
//                                        int which) {
//                        showMessage("Нажали Нет");
//                    }
//                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //    showMessage("Нажали ОК");
                        if (intent_or_null != null) {
                            // intent_or_null.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            try {


                                mActivity.startActivity(intent_or_null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mActivity.finish();
                            bs.dismiss();
                            bs = null;
                        } else {
                            // mActivity.finish();
                            bs.dismiss();
                            bs = null;
                        }
                    }
                });
        try {
            if (bs == null) {
                bs = builder.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs;
    }

    public AlertDialog createThreeButtonAlertDialog(Activity activity, String title, String content, final Intent positiveIntent, final Intent negativeveIntent, final boolean isPositiveActivityForResult, final boolean hideCloseButton) {
        TetDebugUtil.d(pseudo_tag, "title=[" + title + "] content=[" + content + "]");

        if (builder == null) {
            mActivity = activity;
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                //   showMessage("Нажали ОК");
                if (negativeveIntent != null) {
                    negativeveIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mActivity.startActivity(negativeveIntent);
                    mActivity.finish();
                } else {
                    bs.dismiss();
                }
            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //   showMessage("Нажали ОК");
                        if (positiveIntent != null) {
                            if (isPositiveActivityForResult) {
                                mActivity.startActivityForResult(positiveIntent, 22);
                            } else {
                                mActivity.startActivity(positiveIntent);
                                mActivity.finish();
                            }
                            //mActivity.finish();
                            bs.dismiss();
                        } else {
                            bs.dismiss();
                        }
                    }
                });
        if (!hideCloseButton) {
            builder.setNeutralButton(R.string.exitButonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bs.dismiss();
                    mActivity.finish();
                }
            });
        }
        try {
            bs = builder.create();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bs;
    }

    public AlertDialog createAllertDialogSpetialForTCP(Activity activity, String title, String content, final Intent positiveIntent, final Intent negativeveIntent, final boolean isPositiveActivityForResult, final boolean hideCloseButton) {
        TetDebugUtil.d(pseudo_tag, "title=[" + title + "] content=[" + content + "]");

        if (builder == null) {
            mActivity = activity;
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setNegativeButton(activity.getResources().getText(R.string.b_fromList), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                //   showMessage("Нажали ОК");
                if (negativeveIntent != null) {
                    negativeveIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mActivity.startActivity(negativeveIntent);
                    mActivity.finish();
                } else {
                    bs.dismiss();
                }
            }
        });
        builder.setPositiveButton(activity.getResources().getText(R.string.b_from_map), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int which) {
                //   showMessage("Нажали ОК");
                if (positiveIntent != null) {
                    if (isPositiveActivityForResult) {
                        mActivity.startActivityForResult(positiveIntent, 22);
                    } else {
                        mActivity.startActivity(positiveIntent);
                        mActivity.finish();
                    }
                    //mActivity.finish();
                    bs.dismiss();
                } else {
                    bs.dismiss();
                }
            }
        });
        if (!hideCloseButton) {
            builder.setNeutralButton(activity.getResources().getText(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bs.dismiss();
                    mActivity.finish();
                }
            });
        }
        try {
            bs = builder.create();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bs;
    }
}
