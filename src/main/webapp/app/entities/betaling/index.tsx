import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Betaling from './betaling';
import BetalingDetail from './betaling-detail';
import BetalingUpdate from './betaling-update';
import BetalingDeleteDialog from './betaling-delete-dialog';

const BetalingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Betaling />} />
    <Route path="new" element={<BetalingUpdate />} />
    <Route path=":id">
      <Route index element={<BetalingDetail />} />
      <Route path="edit" element={<BetalingUpdate />} />
      <Route path="delete" element={<BetalingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BetalingRoutes;
