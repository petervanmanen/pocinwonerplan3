import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kponstaanuit from './kponstaanuit';
import KponstaanuitDetail from './kponstaanuit-detail';
import KponstaanuitUpdate from './kponstaanuit-update';
import KponstaanuitDeleteDialog from './kponstaanuit-delete-dialog';

const KponstaanuitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kponstaanuit />} />
    <Route path="new" element={<KponstaanuitUpdate />} />
    <Route path=":id">
      <Route index element={<KponstaanuitDetail />} />
      <Route path="edit" element={<KponstaanuitUpdate />} />
      <Route path="delete" element={<KponstaanuitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KponstaanuitRoutes;
