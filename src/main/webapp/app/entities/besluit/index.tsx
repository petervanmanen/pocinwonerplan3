import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Besluit from './besluit';
import BesluitDetail from './besluit-detail';
import BesluitUpdate from './besluit-update';
import BesluitDeleteDialog from './besluit-delete-dialog';

const BesluitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Besluit />} />
    <Route path="new" element={<BesluitUpdate />} />
    <Route path=":id">
      <Route index element={<BesluitDetail />} />
      <Route path="edit" element={<BesluitUpdate />} />
      <Route path="delete" element={<BesluitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BesluitRoutes;
