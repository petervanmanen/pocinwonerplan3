import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Belangtype from './belangtype';
import BelangtypeDetail from './belangtype-detail';
import BelangtypeUpdate from './belangtype-update';
import BelangtypeDeleteDialog from './belangtype-delete-dialog';

const BelangtypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Belangtype />} />
    <Route path="new" element={<BelangtypeUpdate />} />
    <Route path=":id">
      <Route index element={<BelangtypeDetail />} />
      <Route path="edit" element={<BelangtypeUpdate />} />
      <Route path="delete" element={<BelangtypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BelangtypeRoutes;
