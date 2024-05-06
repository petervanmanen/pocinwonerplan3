import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wijk from './wijk';
import WijkDetail from './wijk-detail';
import WijkUpdate from './wijk-update';
import WijkDeleteDialog from './wijk-delete-dialog';

const WijkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wijk />} />
    <Route path="new" element={<WijkUpdate />} />
    <Route path=":id">
      <Route index element={<WijkDetail />} />
      <Route path="edit" element={<WijkUpdate />} />
      <Route path="delete" element={<WijkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WijkRoutes;
