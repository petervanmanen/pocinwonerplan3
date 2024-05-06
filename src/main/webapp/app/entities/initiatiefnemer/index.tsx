import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Initiatiefnemer from './initiatiefnemer';
import InitiatiefnemerDetail from './initiatiefnemer-detail';
import InitiatiefnemerUpdate from './initiatiefnemer-update';
import InitiatiefnemerDeleteDialog from './initiatiefnemer-delete-dialog';

const InitiatiefnemerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Initiatiefnemer />} />
    <Route path="new" element={<InitiatiefnemerUpdate />} />
    <Route path=":id">
      <Route index element={<InitiatiefnemerDetail />} />
      <Route path="edit" element={<InitiatiefnemerUpdate />} />
      <Route path="delete" element={<InitiatiefnemerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InitiatiefnemerRoutes;
