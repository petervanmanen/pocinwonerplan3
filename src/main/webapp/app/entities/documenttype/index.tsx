import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Documenttype from './documenttype';
import DocumenttypeDetail from './documenttype-detail';
import DocumenttypeUpdate from './documenttype-update';
import DocumenttypeDeleteDialog from './documenttype-delete-dialog';

const DocumenttypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Documenttype />} />
    <Route path="new" element={<DocumenttypeUpdate />} />
    <Route path=":id">
      <Route index element={<DocumenttypeDetail />} />
      <Route path="edit" element={<DocumenttypeUpdate />} />
      <Route path="delete" element={<DocumenttypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DocumenttypeRoutes;
