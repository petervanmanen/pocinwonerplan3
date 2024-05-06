import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Financielesituatie from './financielesituatie';
import FinancielesituatieDetail from './financielesituatie-detail';
import FinancielesituatieUpdate from './financielesituatie-update';
import FinancielesituatieDeleteDialog from './financielesituatie-delete-dialog';

const FinancielesituatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Financielesituatie />} />
    <Route path="new" element={<FinancielesituatieUpdate />} />
    <Route path=":id">
      <Route index element={<FinancielesituatieDetail />} />
      <Route path="edit" element={<FinancielesituatieUpdate />} />
      <Route path="delete" element={<FinancielesituatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FinancielesituatieRoutes;
