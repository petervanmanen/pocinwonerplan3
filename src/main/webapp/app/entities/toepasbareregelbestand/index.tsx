import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Toepasbareregelbestand from './toepasbareregelbestand';
import ToepasbareregelbestandDetail from './toepasbareregelbestand-detail';
import ToepasbareregelbestandUpdate from './toepasbareregelbestand-update';
import ToepasbareregelbestandDeleteDialog from './toepasbareregelbestand-delete-dialog';

const ToepasbareregelbestandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Toepasbareregelbestand />} />
    <Route path="new" element={<ToepasbareregelbestandUpdate />} />
    <Route path=":id">
      <Route index element={<ToepasbareregelbestandDetail />} />
      <Route path="edit" element={<ToepasbareregelbestandUpdate />} />
      <Route path="delete" element={<ToepasbareregelbestandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ToepasbareregelbestandRoutes;
