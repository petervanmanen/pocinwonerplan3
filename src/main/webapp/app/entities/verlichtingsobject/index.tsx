import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verlichtingsobject from './verlichtingsobject';
import VerlichtingsobjectDetail from './verlichtingsobject-detail';
import VerlichtingsobjectUpdate from './verlichtingsobject-update';
import VerlichtingsobjectDeleteDialog from './verlichtingsobject-delete-dialog';

const VerlichtingsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verlichtingsobject />} />
    <Route path="new" element={<VerlichtingsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<VerlichtingsobjectDetail />} />
      <Route path="edit" element={<VerlichtingsobjectUpdate />} />
      <Route path="delete" element={<VerlichtingsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerlichtingsobjectRoutes;
