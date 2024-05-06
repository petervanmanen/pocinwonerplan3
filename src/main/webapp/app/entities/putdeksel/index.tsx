import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Putdeksel from './putdeksel';
import PutdekselDetail from './putdeksel-detail';
import PutdekselUpdate from './putdeksel-update';
import PutdekselDeleteDialog from './putdeksel-delete-dialog';

const PutdekselRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Putdeksel />} />
    <Route path="new" element={<PutdekselUpdate />} />
    <Route path=":id">
      <Route index element={<PutdekselDetail />} />
      <Route path="edit" element={<PutdekselUpdate />} />
      <Route path="delete" element={<PutdekselDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PutdekselRoutes;
