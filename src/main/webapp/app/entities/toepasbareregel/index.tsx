import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Toepasbareregel from './toepasbareregel';
import ToepasbareregelDetail from './toepasbareregel-detail';
import ToepasbareregelUpdate from './toepasbareregel-update';
import ToepasbareregelDeleteDialog from './toepasbareregel-delete-dialog';

const ToepasbareregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Toepasbareregel />} />
    <Route path="new" element={<ToepasbareregelUpdate />} />
    <Route path=":id">
      <Route index element={<ToepasbareregelDetail />} />
      <Route path="edit" element={<ToepasbareregelUpdate />} />
      <Route path="delete" element={<ToepasbareregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ToepasbareregelRoutes;
