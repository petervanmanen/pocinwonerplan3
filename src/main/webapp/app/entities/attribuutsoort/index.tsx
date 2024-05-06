import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Attribuutsoort from './attribuutsoort';
import AttribuutsoortDetail from './attribuutsoort-detail';
import AttribuutsoortUpdate from './attribuutsoort-update';
import AttribuutsoortDeleteDialog from './attribuutsoort-delete-dialog';

const AttribuutsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Attribuutsoort />} />
    <Route path="new" element={<AttribuutsoortUpdate />} />
    <Route path=":id">
      <Route index element={<AttribuutsoortDetail />} />
      <Route path="edit" element={<AttribuutsoortUpdate />} />
      <Route path="delete" element={<AttribuutsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttribuutsoortRoutes;
