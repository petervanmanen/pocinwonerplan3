import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Scheiding from './scheiding';
import ScheidingDetail from './scheiding-detail';
import ScheidingUpdate from './scheiding-update';
import ScheidingDeleteDialog from './scheiding-delete-dialog';

const ScheidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Scheiding />} />
    <Route path="new" element={<ScheidingUpdate />} />
    <Route path=":id">
      <Route index element={<ScheidingDetail />} />
      <Route path="edit" element={<ScheidingUpdate />} />
      <Route path="delete" element={<ScheidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ScheidingRoutes;
