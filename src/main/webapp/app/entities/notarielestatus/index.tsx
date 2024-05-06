import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Notarielestatus from './notarielestatus';
import NotarielestatusDetail from './notarielestatus-detail';
import NotarielestatusUpdate from './notarielestatus-update';
import NotarielestatusDeleteDialog from './notarielestatus-delete-dialog';

const NotarielestatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Notarielestatus />} />
    <Route path="new" element={<NotarielestatusUpdate />} />
    <Route path=":id">
      <Route index element={<NotarielestatusDetail />} />
      <Route path="edit" element={<NotarielestatusUpdate />} />
      <Route path="delete" element={<NotarielestatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotarielestatusRoutes;
