import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const dateRangeValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  if (!control.parent) return null;

  const dateFrom = control.parent.get('dateFrom')?.value;
  const dateTo = control.value;

  if (!dateTo) return { required: true };

  if (dateFrom && dateTo < dateFrom) {
    return { invalidRange: true };
  }

  return null;
};
