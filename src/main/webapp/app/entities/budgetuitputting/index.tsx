import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Budgetuitputting from './budgetuitputting';
import BudgetuitputtingDetail from './budgetuitputting-detail';
import BudgetuitputtingUpdate from './budgetuitputting-update';
import BudgetuitputtingDeleteDialog from './budgetuitputting-delete-dialog';

const BudgetuitputtingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Budgetuitputting />} />
    <Route path="new" element={<BudgetuitputtingUpdate />} />
    <Route path=":id">
      <Route index element={<BudgetuitputtingDetail />} />
      <Route path="edit" element={<BudgetuitputtingUpdate />} />
      <Route path="delete" element={<BudgetuitputtingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BudgetuitputtingRoutes;
