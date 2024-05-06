import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Toegangsmiddel from './toegangsmiddel';
import ToegangsmiddelDetail from './toegangsmiddel-detail';
import ToegangsmiddelUpdate from './toegangsmiddel-update';
import ToegangsmiddelDeleteDialog from './toegangsmiddel-delete-dialog';

const ToegangsmiddelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Toegangsmiddel />} />
    <Route path="new" element={<ToegangsmiddelUpdate />} />
    <Route path=":id">
      <Route index element={<ToegangsmiddelDetail />} />
      <Route path="edit" element={<ToegangsmiddelUpdate />} />
      <Route path="delete" element={<ToegangsmiddelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ToegangsmiddelRoutes;
