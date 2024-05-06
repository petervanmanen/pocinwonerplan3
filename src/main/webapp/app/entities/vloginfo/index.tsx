import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vloginfo from './vloginfo';
import VloginfoDetail from './vloginfo-detail';
import VloginfoUpdate from './vloginfo-update';
import VloginfoDeleteDialog from './vloginfo-delete-dialog';

const VloginfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vloginfo />} />
    <Route path="new" element={<VloginfoUpdate />} />
    <Route path=":id">
      <Route index element={<VloginfoDetail />} />
      <Route path="edit" element={<VloginfoUpdate />} />
      <Route path="delete" element={<VloginfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VloginfoRoutes;
