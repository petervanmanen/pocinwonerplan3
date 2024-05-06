import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebruikerrol from './gebruikerrol';
import GebruikerrolDetail from './gebruikerrol-detail';
import GebruikerrolUpdate from './gebruikerrol-update';
import GebruikerrolDeleteDialog from './gebruikerrol-delete-dialog';

const GebruikerrolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebruikerrol />} />
    <Route path="new" element={<GebruikerrolUpdate />} />
    <Route path=":id">
      <Route index element={<GebruikerrolDetail />} />
      <Route path="edit" element={<GebruikerrolUpdate />} />
      <Route path="delete" element={<GebruikerrolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebruikerrolRoutes;
