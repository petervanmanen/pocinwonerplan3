import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Individueelkeuzebudget from './individueelkeuzebudget';
import IndividueelkeuzebudgetDetail from './individueelkeuzebudget-detail';
import IndividueelkeuzebudgetUpdate from './individueelkeuzebudget-update';
import IndividueelkeuzebudgetDeleteDialog from './individueelkeuzebudget-delete-dialog';

const IndividueelkeuzebudgetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Individueelkeuzebudget />} />
    <Route path="new" element={<IndividueelkeuzebudgetUpdate />} />
    <Route path=":id">
      <Route index element={<IndividueelkeuzebudgetDetail />} />
      <Route path="edit" element={<IndividueelkeuzebudgetUpdate />} />
      <Route path="delete" element={<IndividueelkeuzebudgetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndividueelkeuzebudgetRoutes;
