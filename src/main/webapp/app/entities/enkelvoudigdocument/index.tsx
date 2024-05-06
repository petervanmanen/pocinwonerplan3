import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Enkelvoudigdocument from './enkelvoudigdocument';
import EnkelvoudigdocumentDetail from './enkelvoudigdocument-detail';
import EnkelvoudigdocumentUpdate from './enkelvoudigdocument-update';
import EnkelvoudigdocumentDeleteDialog from './enkelvoudigdocument-delete-dialog';

const EnkelvoudigdocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Enkelvoudigdocument />} />
    <Route path="new" element={<EnkelvoudigdocumentUpdate />} />
    <Route path=":id">
      <Route index element={<EnkelvoudigdocumentDetail />} />
      <Route path="edit" element={<EnkelvoudigdocumentUpdate />} />
      <Route path="delete" element={<EnkelvoudigdocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnkelvoudigdocumentRoutes;
