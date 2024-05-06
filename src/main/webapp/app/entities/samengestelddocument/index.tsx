import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Samengestelddocument from './samengestelddocument';
import SamengestelddocumentDetail from './samengestelddocument-detail';
import SamengestelddocumentUpdate from './samengestelddocument-update';
import SamengestelddocumentDeleteDialog from './samengestelddocument-delete-dialog';

const SamengestelddocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Samengestelddocument />} />
    <Route path="new" element={<SamengestelddocumentUpdate />} />
    <Route path=":id">
      <Route index element={<SamengestelddocumentDetail />} />
      <Route path="edit" element={<SamengestelddocumentUpdate />} />
      <Route path="delete" element={<SamengestelddocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SamengestelddocumentRoutes;
