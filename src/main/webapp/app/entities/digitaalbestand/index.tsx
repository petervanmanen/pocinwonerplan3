import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Digitaalbestand from './digitaalbestand';
import DigitaalbestandDetail from './digitaalbestand-detail';
import DigitaalbestandUpdate from './digitaalbestand-update';
import DigitaalbestandDeleteDialog from './digitaalbestand-delete-dialog';

const DigitaalbestandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Digitaalbestand />} />
    <Route path="new" element={<DigitaalbestandUpdate />} />
    <Route path=":id">
      <Route index element={<DigitaalbestandDetail />} />
      <Route path="edit" element={<DigitaalbestandUpdate />} />
      <Route path="delete" element={<DigitaalbestandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DigitaalbestandRoutes;
