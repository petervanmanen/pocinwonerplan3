import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reisdocument from './reisdocument';
import ReisdocumentDetail from './reisdocument-detail';
import ReisdocumentUpdate from './reisdocument-update';
import ReisdocumentDeleteDialog from './reisdocument-delete-dialog';

const ReisdocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reisdocument />} />
    <Route path="new" element={<ReisdocumentUpdate />} />
    <Route path=":id">
      <Route index element={<ReisdocumentDetail />} />
      <Route path="edit" element={<ReisdocumentUpdate />} />
      <Route path="delete" element={<ReisdocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReisdocumentRoutes;
