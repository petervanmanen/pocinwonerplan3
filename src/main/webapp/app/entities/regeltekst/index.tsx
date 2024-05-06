import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Regeltekst from './regeltekst';
import RegeltekstDetail from './regeltekst-detail';
import RegeltekstUpdate from './regeltekst-update';
import RegeltekstDeleteDialog from './regeltekst-delete-dialog';

const RegeltekstRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Regeltekst />} />
    <Route path="new" element={<RegeltekstUpdate />} />
    <Route path=":id">
      <Route index element={<RegeltekstDetail />} />
      <Route path="edit" element={<RegeltekstUpdate />} />
      <Route path="delete" element={<RegeltekstDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegeltekstRoutes;
