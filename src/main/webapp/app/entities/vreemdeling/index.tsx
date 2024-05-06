import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vreemdeling from './vreemdeling';
import VreemdelingDetail from './vreemdeling-detail';
import VreemdelingUpdate from './vreemdeling-update';
import VreemdelingDeleteDialog from './vreemdeling-delete-dialog';

const VreemdelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vreemdeling />} />
    <Route path="new" element={<VreemdelingUpdate />} />
    <Route path=":id">
      <Route index element={<VreemdelingDetail />} />
      <Route path="edit" element={<VreemdelingUpdate />} />
      <Route path="delete" element={<VreemdelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VreemdelingRoutes;
