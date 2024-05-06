import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Belprovider from './belprovider';
import BelproviderDetail from './belprovider-detail';
import BelproviderUpdate from './belprovider-update';
import BelproviderDeleteDialog from './belprovider-delete-dialog';

const BelproviderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Belprovider />} />
    <Route path="new" element={<BelproviderUpdate />} />
    <Route path=":id">
      <Route index element={<BelproviderDetail />} />
      <Route path="edit" element={<BelproviderUpdate />} />
      <Route path="delete" element={<BelproviderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BelproviderRoutes;
