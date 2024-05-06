import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Behandelsoort from './behandelsoort';
import BehandelsoortDetail from './behandelsoort-detail';
import BehandelsoortUpdate from './behandelsoort-update';
import BehandelsoortDeleteDialog from './behandelsoort-delete-dialog';

const BehandelsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Behandelsoort />} />
    <Route path="new" element={<BehandelsoortUpdate />} />
    <Route path=":id">
      <Route index element={<BehandelsoortDetail />} />
      <Route path="edit" element={<BehandelsoortUpdate />} />
      <Route path="delete" element={<BehandelsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BehandelsoortRoutes;
