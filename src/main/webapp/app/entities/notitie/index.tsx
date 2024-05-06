import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Notitie from './notitie';
import NotitieDetail from './notitie-detail';
import NotitieUpdate from './notitie-update';
import NotitieDeleteDialog from './notitie-delete-dialog';

const NotitieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Notitie />} />
    <Route path="new" element={<NotitieUpdate />} />
    <Route path=":id">
      <Route index element={<NotitieDetail />} />
      <Route path="edit" element={<NotitieUpdate />} />
      <Route path="delete" element={<NotitieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotitieRoutes;
