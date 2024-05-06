import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reisdocumentsoort from './reisdocumentsoort';
import ReisdocumentsoortDetail from './reisdocumentsoort-detail';
import ReisdocumentsoortUpdate from './reisdocumentsoort-update';
import ReisdocumentsoortDeleteDialog from './reisdocumentsoort-delete-dialog';

const ReisdocumentsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reisdocumentsoort />} />
    <Route path="new" element={<ReisdocumentsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ReisdocumentsoortDetail />} />
      <Route path="edit" element={<ReisdocumentsoortUpdate />} />
      <Route path="delete" element={<ReisdocumentsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReisdocumentsoortRoutes;
