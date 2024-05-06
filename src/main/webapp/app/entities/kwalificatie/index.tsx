import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kwalificatie from './kwalificatie';
import KwalificatieDetail from './kwalificatie-detail';
import KwalificatieUpdate from './kwalificatie-update';
import KwalificatieDeleteDialog from './kwalificatie-delete-dialog';

const KwalificatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kwalificatie />} />
    <Route path="new" element={<KwalificatieUpdate />} />
    <Route path=":id">
      <Route index element={<KwalificatieDetail />} />
      <Route path="edit" element={<KwalificatieUpdate />} />
      <Route path="delete" element={<KwalificatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KwalificatieRoutes;
