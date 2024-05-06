import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Klantbeoordeling from './klantbeoordeling';
import KlantbeoordelingDetail from './klantbeoordeling-detail';
import KlantbeoordelingUpdate from './klantbeoordeling-update';
import KlantbeoordelingDeleteDialog from './klantbeoordeling-delete-dialog';

const KlantbeoordelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Klantbeoordeling />} />
    <Route path="new" element={<KlantbeoordelingUpdate />} />
    <Route path=":id">
      <Route index element={<KlantbeoordelingDetail />} />
      <Route path="edit" element={<KlantbeoordelingUpdate />} />
      <Route path="delete" element={<KlantbeoordelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KlantbeoordelingRoutes;
