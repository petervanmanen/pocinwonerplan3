import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Waterinrichtingsobject from './waterinrichtingsobject';
import WaterinrichtingsobjectDetail from './waterinrichtingsobject-detail';
import WaterinrichtingsobjectUpdate from './waterinrichtingsobject-update';
import WaterinrichtingsobjectDeleteDialog from './waterinrichtingsobject-delete-dialog';

const WaterinrichtingsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Waterinrichtingsobject />} />
    <Route path="new" element={<WaterinrichtingsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<WaterinrichtingsobjectDetail />} />
      <Route path="edit" element={<WaterinrichtingsobjectUpdate />} />
      <Route path="delete" element={<WaterinrichtingsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WaterinrichtingsobjectRoutes;
