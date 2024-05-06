import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Betaalmoment from './betaalmoment';
import BetaalmomentDetail from './betaalmoment-detail';
import BetaalmomentUpdate from './betaalmoment-update';
import BetaalmomentDeleteDialog from './betaalmoment-delete-dialog';

const BetaalmomentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Betaalmoment />} />
    <Route path="new" element={<BetaalmomentUpdate />} />
    <Route path=":id">
      <Route index element={<BetaalmomentDetail />} />
      <Route path="edit" element={<BetaalmomentUpdate />} />
      <Route path="delete" element={<BetaalmomentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BetaalmomentRoutes;
