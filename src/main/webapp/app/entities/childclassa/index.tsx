import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Childclassa from './childclassa';
import ChildclassaDetail from './childclassa-detail';
import ChildclassaUpdate from './childclassa-update';
import ChildclassaDeleteDialog from './childclassa-delete-dialog';

const ChildclassaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Childclassa />} />
    <Route path="new" element={<ChildclassaUpdate />} />
    <Route path=":id">
      <Route index element={<ChildclassaDetail />} />
      <Route path="edit" element={<ChildclassaUpdate />} />
      <Route path="delete" element={<ChildclassaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChildclassaRoutes;
