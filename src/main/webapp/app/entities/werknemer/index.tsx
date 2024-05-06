import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Werknemer from './werknemer';
import WerknemerDetail from './werknemer-detail';
import WerknemerUpdate from './werknemer-update';
import WerknemerDeleteDialog from './werknemer-delete-dialog';

const WerknemerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Werknemer />} />
    <Route path="new" element={<WerknemerUpdate />} />
    <Route path=":id">
      <Route index element={<WerknemerDetail />} />
      <Route path="edit" element={<WerknemerUpdate />} />
      <Route path="delete" element={<WerknemerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WerknemerRoutes;
