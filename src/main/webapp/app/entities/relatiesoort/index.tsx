import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Relatiesoort from './relatiesoort';
import RelatiesoortDetail from './relatiesoort-detail';
import RelatiesoortUpdate from './relatiesoort-update';
import RelatiesoortDeleteDialog from './relatiesoort-delete-dialog';

const RelatiesoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Relatiesoort />} />
    <Route path="new" element={<RelatiesoortUpdate />} />
    <Route path=":id">
      <Route index element={<RelatiesoortDetail />} />
      <Route path="edit" element={<RelatiesoortUpdate />} />
      <Route path="delete" element={<RelatiesoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RelatiesoortRoutes;
