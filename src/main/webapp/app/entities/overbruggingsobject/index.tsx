import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Overbruggingsobject from './overbruggingsobject';
import OverbruggingsobjectDetail from './overbruggingsobject-detail';
import OverbruggingsobjectUpdate from './overbruggingsobject-update';
import OverbruggingsobjectDeleteDialog from './overbruggingsobject-delete-dialog';

const OverbruggingsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Overbruggingsobject />} />
    <Route path="new" element={<OverbruggingsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<OverbruggingsobjectDetail />} />
      <Route path="edit" element={<OverbruggingsobjectUpdate />} />
      <Route path="delete" element={<OverbruggingsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OverbruggingsobjectRoutes;
