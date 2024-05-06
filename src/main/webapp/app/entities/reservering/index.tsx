import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reservering from './reservering';
import ReserveringDetail from './reservering-detail';
import ReserveringUpdate from './reservering-update';
import ReserveringDeleteDialog from './reservering-delete-dialog';

const ReserveringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reservering />} />
    <Route path="new" element={<ReserveringUpdate />} />
    <Route path=":id">
      <Route index element={<ReserveringDetail />} />
      <Route path="edit" element={<ReserveringUpdate />} />
      <Route path="delete" element={<ReserveringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReserveringRoutes;
