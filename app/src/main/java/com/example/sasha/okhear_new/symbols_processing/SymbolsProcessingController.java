package com.example.sasha.okhear_new.symbols_processing;

import android.util.Log;

import com.example.sasha.okhear_new.utils.Utils;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class SymbolsProcessingController {

    private Map<Character, SymbolValidator> symbolValidators = new HashMap<>();

    private SymbolValidator zeroValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };
    
    private SymbolValidator oneValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator twoValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator threeValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator fourValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator fiveValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator sixValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator sevenValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator eightValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator nineValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator aValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('А');
            if (isFrontCamera) {
                return symbolPos <= 2;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator beValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Б');
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('П') <= 3  || sortedSymbols.indexOf('У') <= 4 ||
                         sortedSymbols.indexOf('Т') <= 4 ) {
                    return false;
                } else {
                    return symbolPos <= 5;
                }
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator veValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('В');
            if (isFrontCamera) {
                return symbolPos <= 2;
            } else {
                return symbolPos <= 3;
            }

        }
    };

    private SymbolValidator geValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Г');
            if (isFrontCamera) {
                return symbolPos <= 4;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator deValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Д');
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('Э') <= 3  || sortedSymbols.indexOf('П') <= 4 ||
                        sortedSymbols.indexOf('Ц') <= 4 || sortedSymbols.indexOf('Т') <= 4 ) {
                    return false;
                } else {
                    return symbolPos <= 10;
                }
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator eValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Е');
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('Ы') <= 3  || sortedSymbols.indexOf('П') <= 4 ||
                        sortedSymbols.indexOf('Ц') <= 4 || sortedSymbols.indexOf('Т') <= 4 ) {
                    return false;
                } else {
                    return symbolPos <= 6;
                }

            } else {
                return symbolPos <= 4;
            }
        }
    };

    private SymbolValidator jeValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ж');
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('Ы') <= 3  || sortedSymbols.indexOf('П') <= 4 ||
                        sortedSymbols.indexOf('Ц') <= 4 || sortedSymbols.indexOf('Т') <= 4 ) {
                    return false;
                } else {
                    return (sortedSymbols.indexOf('Ю') <= 5 && sortedSymbols.indexOf('Ж') <= 15);
                }

            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator zeValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('З');
            if (isFrontCamera) {
                return symbolPos <= 3;
            } else {
                return symbolPos <= 3;
            }

        }
    };

    private SymbolValidator iValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('И');
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('И') <= 4) {
                    return true;
                } else if (sortedSymbols.indexOf('П') <= 2 || sortedSymbols.indexOf('Т') <= 3) {
                    return false;
                } else {
                    return (sortedSymbols.indexOf('О') <= 5 || sortedSymbols.indexOf('C') <= 6 ||
                            sortedSymbols.indexOf('Ю') <= 6 && sortedSymbols.indexOf('И') <= 15);
                }

            } else {
                if (sortedSymbols.indexOf('Ы') <= 3  || sortedSymbols.indexOf('П') <= 4 ||
                        sortedSymbols.indexOf('Ц') <= 4 || sortedSymbols.indexOf('Т') <= 4 ) {
                    return false;
                } else if (sortedSymbols.indexOf('И') <= 4) {
                    return true;
                } else {
                    return ((sortedSymbols.indexOf('О') <= 5 || sortedSymbols.indexOf('C') <= 6) &&
                            sortedSymbols.indexOf('И') <= 15);
                }

            }
        }
    };

    private SymbolValidator ikratkayaValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Й');
            if (isFrontCamera) {
                if (symbolPos <= 5) {
                    return true;
                } else {
                    return (sortedSymbols.indexOf('Ю') <= 5 &&  symbolPos <= 10);
                }

            } else {
                return symbolPos <= 3 || (symbolPos <= 10 && sortedSymbols.indexOf('Р') <= 5);
            }
        }
    };

    private SymbolValidator kValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('К');
            if (isFrontCamera) {
                return symbolPos <= 4;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator lValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Л');
            if (isFrontCamera) {
                return symbolPos <= 4;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator mValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('М');
            if (isFrontCamera) {
                return symbolPos <= 3;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator nValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Н');
            if (isFrontCamera) {
                return symbolPos <= 3;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator oValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('О');
            if (isFrontCamera) {
                if (symbolPos <= 4){
                    return true;
                } else {
                    return (sortedSymbols.indexOf('Ю') <= 4 || sortedSymbols.indexOf('Ы') <= 4
                            && symbolPos <= 10);
                }
            } else {
                return symbolPos <= 3;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////

    private SymbolValidator pValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator rValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator sValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator tValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            return false;
        }
    };

    private SymbolValidator uValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('У');
            if (sortedSymbols.indexOf('Э') <= 1) {
                return false;
            }
            return symbolPos <= 2;
        }
    };

    private SymbolValidator fValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ф');
            return symbolPos <= 3;
        }
    };

    private SymbolValidator hValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Х');
            if (sortedSymbols.indexOf('Ь') <= 1 && sortedSymbols.indexOf('Ь') < symbolPos
                    || sortedSymbols.indexOf('В') == 0
                    || sortedSymbols.indexOf('Э') == 0) {
                return false;
            }
            if (isFrontCamera) {
                return symbolPos <= 5;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator ceValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ц');
            if (sortedSymbols.indexOf('Ы') == 0) {
                return false;
            }
            if (isFrontCamera) {
                return symbolPos <= 3;
            } else {
                return symbolPos <= 1;
            }
        }
    };

    private SymbolValidator cheValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ч');
            if (sortedSymbols.indexOf('А') <= 3
                    || sortedSymbols.indexOf('Х') < symbolPos) {
                return false;
            }
            if (isFrontCamera) {
                return symbolPos <= 4;
            } else {
                if (sortedSymbols.indexOf('Ф') < symbolPos) {
                    return false;
                }
                return symbolPos <= 3;
            }
        }
    };

    private SymbolValidator shValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ш');
            if (sortedSymbols.indexOf('В') < symbolPos
                    || sortedSymbols.indexOf('К') < symbolPos
                    || sortedSymbols.indexOf('А') <= 3) {
                return false;
            }
            return symbolPos <= 2;
        }
    };

    private SymbolValidator iiValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ы');
            if (sortedSymbols.indexOf('Г') <= 1
                    || sortedSymbols.indexOf('Р') <= 1
                    || sortedSymbols.indexOf('Н') <= 1) {
                return false;
            }
            return symbolPos <= 2;
        }
    };

    private SymbolValidator znakValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ь');
            if (sortedSymbols.indexOf('Х') <= 1 && sortedSymbols.indexOf('З') < symbolPos) {
                return false;
            }
            if (!isFrontCamera) {
                if (sortedSymbols.indexOf('З') <= 1 && sortedSymbols.indexOf('З') < symbolPos) {
                    return false;
                }
            }
            return symbolPos <= 3;
        }
    };

    private SymbolValidator aeValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Э');
            if (sortedSymbols.indexOf('Е') <= 3 && sortedSymbols.indexOf('Е') <= symbolPos
                    || sortedSymbols.indexOf('З') <= symbolPos
                    || sortedSymbols.indexOf('К') <= symbolPos
                    || sortedSymbols.indexOf('Ц') <= symbolPos) {
                return false;
            }
            if (isFrontCamera) {
                return symbolPos <= 3;
            } else {
                return symbolPos <= 1;
            }
        }
    };

    private SymbolValidator yuValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Ю');
            if (sortedSymbols.indexOf('У') == 0
                    || sortedSymbols.indexOf('О') <= 1 && sortedSymbols.indexOf('О') < symbolPos) {
                return false;
            }
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('Ж') <= 4) {
                    return false;
                }
            } else {
                if (sortedSymbols.indexOf('Ж') <= 2 && sortedSymbols.indexOf('Ж') < symbolPos) {
                    return false;
                }
            }
            return symbolPos <= 1;
        }
    };

    private SymbolValidator yaValidator = new SymbolValidator() {
        @Override
        public boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera) {
            int symbolPos = sortedSymbols.indexOf('Я');
            if (sortedSymbols.indexOf('В') < symbolPos
                    || sortedSymbols.indexOf('З') <= 3 && sortedSymbols.indexOf('З') < symbolPos) {
                return false;
            }
            if (isFrontCamera) {
                if (sortedSymbols.indexOf('П') <= 1
                        || sortedSymbols.indexOf('К') <= 3 && sortedSymbols.indexOf('К') < symbolPos) {
                    return false;
                }
                return symbolPos <= 4;
            } else {
                return symbolPos <= 3;
            }
        }
    };

    {
        symbolValidators.put('0', zeroValidator);
        symbolValidators.put('1', oneValidator);
        symbolValidators.put('2', twoValidator);
        symbolValidators.put('3', threeValidator);
        symbolValidators.put('4', fourValidator);
        symbolValidators.put('5', fiveValidator);
        symbolValidators.put('6', sixValidator);
        symbolValidators.put('7', sevenValidator);
        symbolValidators.put('8', eightValidator);
        symbolValidators.put('9', nineValidator);
        symbolValidators.put('А', aValidator);
        symbolValidators.put('Б', beValidator);
        symbolValidators.put('В', veValidator);
        symbolValidators.put('Г', geValidator);
        symbolValidators.put('Д', deValidator);
        symbolValidators.put('Е', eValidator);
        symbolValidators.put('Ж', jeValidator);
        symbolValidators.put('З', zeValidator);
        symbolValidators.put('И', iValidator);
        symbolValidators.put('Й', ikratkayaValidator);
        symbolValidators.put('К', kValidator);
        symbolValidators.put('Л', lValidator);
        symbolValidators.put('М', mValidator);
        symbolValidators.put('Н', nValidator);
        symbolValidators.put('О', oValidator);
        symbolValidators.put('П', pValidator);
        symbolValidators.put('Р', rValidator);
        symbolValidators.put('С', sValidator);
        symbolValidators.put('Т', tValidator);
        symbolValidators.put('У', uValidator);
        symbolValidators.put('Ф', fValidator);
        symbolValidators.put('Х', hValidator);
        symbolValidators.put('Ц', ceValidator);
        symbolValidators.put('Ч', cheValidator);
        symbolValidators.put('Ш', shValidator);
        symbolValidators.put('Ы', iiValidator);
        symbolValidators.put('Ь', znakValidator);
        symbolValidators.put('Э', aeValidator);
        symbolValidators.put('Ю', yuValidator);
        symbolValidators.put('Я', yaValidator);
    }

    public boolean isSymbolValid(char symbol, String sortedSymbolsString, boolean isFrontCamera) {
        Log.d("sasha", "isSymbolValid: " + sortedSymbolsString);
        SymbolValidator validator = symbolValidators.get(Utils.toUpperCase(symbol));
        List<Character> sortedSymbols = new ArrayList<>();
        for (char c : sortedSymbolsString.toCharArray()) {
            sortedSymbols.add(c);
        }
        if (validator != null) {
            return validator.isSymbolValid(sortedSymbols, isFrontCamera);
        }
        return false;
    }
    
    private interface SymbolValidator {
        boolean isSymbolValid(List<Character> sortedSymbols, boolean isFrontCamera);
    }
}
