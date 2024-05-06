import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verhardingsobject from './verhardingsobject';
import VerhardingsobjectDetail from './verhardingsobject-detail';
import VerhardingsobjectUpdate from './verhardingsobject-update';
import VerhardingsobjectDeleteDialog from './verhardingsobject-delete-dialog';

const VerhardingsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verhardingsobject />} />
    <Route path="new" element={<VerhardingsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<VerhardingsobjectDetail />} />
      <Route path="edit" element={<VerhardingsobjectUpdate />} />
      <Route path="delete" element={<VerhardingsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerhardingsobjectRoutes;
