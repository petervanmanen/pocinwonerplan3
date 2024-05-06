import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overigbouwwerk from './overigbouwwerk';
import OverigbouwwerkDetail from './overigbouwwerk-detail';
import OverigbouwwerkUpdate from './overigbouwwerk-update';
import OverigbouwwerkDeleteDialog from './overigbouwwerk-delete-dialog';

const OverigbouwwerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overigbouwwerk />} />
    <Route path="new" element={<OverigbouwwerkUpdate />} />
    <Route path=":id">
      <Route index element={<OverigbouwwerkDetail />} />
      <Route path="edit" element={<OverigbouwwerkUpdate />} />
      <Route path="delete" element={<OverigbouwwerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverigbouwwerkRoutes;
