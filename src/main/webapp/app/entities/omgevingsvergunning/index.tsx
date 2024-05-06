import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omgevingsvergunning from './omgevingsvergunning';
import OmgevingsvergunningDetail from './omgevingsvergunning-detail';
import OmgevingsvergunningUpdate from './omgevingsvergunning-update';
import OmgevingsvergunningDeleteDialog from './omgevingsvergunning-delete-dialog';

const OmgevingsvergunningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omgevingsvergunning />} />
    <Route path="new" element={<OmgevingsvergunningUpdate />} />
    <Route path=":id">
      <Route index element={<OmgevingsvergunningDetail />} />
      <Route path="edit" element={<OmgevingsvergunningUpdate />} />
      <Route path="delete" element={<OmgevingsvergunningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmgevingsvergunningRoutes;
