import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omgevingsdocument from './omgevingsdocument';
import OmgevingsdocumentDetail from './omgevingsdocument-detail';
import OmgevingsdocumentUpdate from './omgevingsdocument-update';
import OmgevingsdocumentDeleteDialog from './omgevingsdocument-delete-dialog';

const OmgevingsdocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omgevingsdocument />} />
    <Route path="new" element={<OmgevingsdocumentUpdate />} />
    <Route path=":id">
      <Route index element={<OmgevingsdocumentDetail />} />
      <Route path="edit" element={<OmgevingsdocumentUpdate />} />
      <Route path="delete" element={<OmgevingsdocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmgevingsdocumentRoutes;
