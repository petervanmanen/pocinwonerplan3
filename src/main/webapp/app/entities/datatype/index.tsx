import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Datatype from './datatype';
import DatatypeDetail from './datatype-detail';
import DatatypeUpdate from './datatype-update';
import DatatypeDeleteDialog from './datatype-delete-dialog';

const DatatypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Datatype />} />
    <Route path="new" element={<DatatypeUpdate />} />
    <Route path=":id">
      <Route index element={<DatatypeDetail />} />
      <Route path="edit" element={<DatatypeUpdate />} />
      <Route path="delete" element={<DatatypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DatatypeRoutes;
