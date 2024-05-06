import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Actie from './actie';
import ActieDetail from './actie-detail';
import ActieUpdate from './actie-update';
import ActieDeleteDialog from './actie-delete-dialog';

const ActieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Actie />} />
    <Route path="new" element={<ActieUpdate />} />
    <Route path=":id">
      <Route index element={<ActieDetail />} />
      <Route path="edit" element={<ActieUpdate />} />
      <Route path="delete" element={<ActieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActieRoutes;
