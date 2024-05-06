import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Begrotingregel from './begrotingregel';
import BegrotingregelDetail from './begrotingregel-detail';
import BegrotingregelUpdate from './begrotingregel-update';
import BegrotingregelDeleteDialog from './begrotingregel-delete-dialog';

const BegrotingregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Begrotingregel />} />
    <Route path="new" element={<BegrotingregelUpdate />} />
    <Route path=":id">
      <Route index element={<BegrotingregelDetail />} />
      <Route path="edit" element={<BegrotingregelUpdate />} />
      <Route path="delete" element={<BegrotingregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BegrotingregelRoutes;
